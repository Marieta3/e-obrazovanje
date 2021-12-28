<template>
    <div>
        <v-stepper v-model="currentStep" vertical >
                <span v-for="(item, index) in domainProblems" :key="item.id">
                    <v-stepper-step :complete="currentStep > index" :step="index" editable>
                        {{item.title}}
                        <small>{{item.description}}</small>
                    </v-stepper-step>
                    <v-stepper-content :step="index">
                        <v-card color="grey lighten-1" class="mb-12" height="400px" v-if="renderComponent">
                            <v-card-text>
                                <div v-for="(domainQuestion,index2) in domainProblemQuestions.get(item.id)" :key="index2">
                                    <question-dialog :index="index2" :domainProblemId="item.id" :currentQuestion="domainQuestion" v-on:commitedQuestion="updateQuestion(item.id, index2, $event)" class="ma-2"/>
                                </div>
                            </v-card-text>
                        </v-card>
                        <span class="d-flex justify-space-around mb-6">
                            <v-btn color="primary" @click="addQuestion(item.id)">Add question</v-btn>
                            <v-btn color="primary" :disabled="currentStep >= domainProblems.length - 1"  @click="continueClk()">Continue</v-btn>
                            <v-btn @click="currentStep=-1">Cancel</v-btn>
                        </span>
                    </v-stepper-content>
                </span>
        </v-stepper>
        <v-btn v-if="!testId" @click="commit()" color="warning" class="ma-3">Finish</v-btn>
    </div>
</template>

<script>
import * as comm from '../../configuration/communication.js'
import QuestionDialog from './QuestionDialog.vue'
import axios from 'axios'
  export default {
    components: {
        QuestionDialog
    },
    props: ['courseId','testId'],
    data () {
      return {
        title: "",
        currentStep: -1,
        domainProblems: [],
        domainProblemQuestions: new Map(),
        renderComponent: true,
      }
    },
    created(){
        this.getDomainProblemsForCourse()
        if(this.testId){
            this.getTestById();
        }
    },
    methods:{
        continueClk(){
            this.currentStep += 1
            console.log("current step: "+this.currentStep+" size:"+this.domainProblems.length)
        },
        getDomainProblemsForCourse(){
            axios.get(comm.protocol +'://' + comm.server + '/courses/'+this.courseId+'/domain',{headers: comm.getHeader()})
                .then(response => {
                    if(response.status==200){
                        this.domainProblems = response.data;
                        for(let dp of response.data){
                            this.domainProblemQuestions.set(dp.id, [])
                        }
                    }
                }).catch(()=>{
                    alert("greska")
                })
        },
        addQuestion(domainProblemId){
            let dpq = this.domainProblemQuestions.get(domainProblemId)
            let newQuestion = {
                text: "",
                answers: [],
                isRandom: false,
                points: "",
            }
            dpq.push(newQuestion)
            this.forceRerender()
        },
        getTestById(){
            let config = { headers: comm.getHeader() }
            axios.get(comm.protocol +'://' + comm.server + '/tests/'+this.testId,config)
            .then(response => {
              if(response.status==200){
                this.mapResponseToDomainProblemQuestions(response.data)
              }
            }).catch(() => {
              alert("greska")
            })
        },
        updateQuestion(domainProblemId, index, item){
            let questions = this.domainProblemQuestions.get(domainProblemId)
            questions[index] = item
        },
        commit(){
            let data=this.getTest()
            let config = {headers: comm.getHeader()}
            axios.post(comm.protocol +'://' + comm.server + '/tests', data,config)
            .then(response => {
              if(response.status==200){
                alert("uspesno kreiran test")
              }
            }).catch(() => {
              alert("greska")
            })
        },
        mapResponseToDomainProblemQuestions(testResponse){
            this.title=testResponse.title
            for(let question of testResponse.questions){
                let dpg = this.domainProblemQuestions.get(question.domainProblemId)
                dpg.push(question)
            }
        },
        getTest() {
            let test={
                title: this.title,
                questions: [],
                courseId: this.courseId
            }
            for(let key of this.domainProblemQuestions.keys()) {
                for(let question of this.domainProblemQuestions.get(key)) {
                    question.domainProblemId=key
                    test.questions.push(question)
                }
            }
            return test
        },
        forceRerender() {
            // Removing my-component from the DOM
            this.renderComponent = false;

            this.$nextTick(() => {
            // Adding the component back in
                this.renderComponent = true;
            });
        },
    }
  }
</script>

<style>
</style>