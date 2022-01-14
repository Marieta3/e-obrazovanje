<template> 
    <v-container>
        <v-row justify="center">
            <v-col v-if="!testStarted">
                <v-btn color="success"  @click="startTest()" >Start test</v-btn>
            </v-col>
            <v-col  v-else>
                <v-container>
                    <v-row>
                        <v-col v-if="renderComponent">
                            <question :currentQuestion="question" v-on:questionChanged="updateMyAnswers($event)"/>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col>
                            <v-btn color="success" @click="answer()">Confirm answer</v-btn>
                        </v-col>
                    </v-row>
                </v-container>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import axios from 'axios'
    import * as comm from '../configuration/communication.js'
    import Question from '../components/Question.vue'
    export default {
        props: ['testId'],
        components:{
            Question
        },
        data() {
            return {
                currentStep: 0,
                questions: [],
                question: {},
                steps: 0,
                testStarted: false,
                startTime: null,
                testResultId: null,
                renderComponent: true
            }
        },
        methods:{
            getTestQuestions(){
                let config = {headers: comm.getHeader()}
                axios.get(comm.protocol +'://' + comm.server + '/tests/'+this.testId,config)
                .then(response => {
                if(response.status==200){
                    console.log(response.data)
                    this.questions = response.data.questions
                    this.steps = this.questions.length
                    this.testStarted = true
                    this.startTime = new Date().getTime()
                    this.currentStep = 1
                }
                }).catch(() => {
                alert("greska")
                })
            },
            updateMyAnswers(updatedQuestion){
                this.question = updatedQuestion
            },
            finishTest(){
                let myAnswers = this.getMyAnswers()
                let endTime = new Date().getTime()
                let config = { headers: comm.getHeader() }
                let data = {startTime: this.startTime, endTime: endTime, answerIDs: myAnswers, testID: this.testId}
                axios.post(comm.protocol +'://' + comm.server + '/test-results', data ,config)
                .then(response => {
                if(response.status==200){
                    alert("uspesno zavrsen test")
                }
                }).catch(() => {
                alert("greska")
                })
            },
            getMyAnswers(){
                let result = []
                for(let q of this.questions){
                    result.push(this.getSelectedAnswersForQuestion(q))
                }
                return result
            },
            getSelectedAnswersForQuestion(question){
                let result = [] 
                for(let answer of question.answers){
                    if (answer.correct == true){
                        result.push(answer.id)                           
                    }
                }
                return result
            },
            getFirstQuestion(){
                let data = {testId: this.testId}
                let config = { headers: comm.getHeader() }
                axios.post(comm.protocol +'://' + comm.server + '/test-results', data ,config)
                    .then(response => {
                        if(response.status==200){
                            this.question = response.data.question
                            this.testResultId = response.data.testResultId
                            this.testStarted = true
                        }
                    }).catch(() => {
                        alert("greska")
                    })
            },
            answer(){
                let data = {testId: this.testId, testResultId: this.testResultId, answerIds: this.getSelectedAnswersForQuestion(this.question)}
                let config = { headers: comm.getHeader() }
                axios.post(comm.protocol +'://' + comm.server + '/test-results', data ,config)
                    .then(response => {
                        if(response.status==200){
                            this.question = response.data.question
                            this.forceRerender();
                        }
                    }).catch(() => {
                        alert("greska")
                    })
            },
            startTest(){
                //TODO: proveri da li je ks izgenerisan ili rucno kreiran i na osnovu toga dobavi pitanja
               // this.getTestQuestions()
               this.getFirstQuestion()
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