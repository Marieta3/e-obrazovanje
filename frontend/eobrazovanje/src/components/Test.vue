<template>
  <v-container>
      <v-row justify="center">
          <v-col cols="12" md="6" >
              <v-text-field
                label="Title"
                v-model="title"
                filled
            ></v-text-field>
          </v-col>
      </v-row>
      <template v-if="renderComponent">
      <v-row v-for="(item,index) in questions" class="pa-3" :key="index" justify="center" >
          <v-col cols="12" md="6">
              <question-dialog :index="index" :oldQuestion="item" v-on:commitedQuestion="updateQuestion(index,$event)"/>
          </v-col>
      </v-row>
      </template>
      <v-row>
          <v-col cols="12" md="4" />
          <v-col cols="12" md="2">
              <v-btn color="primary" @click="addQuestion()">
                  Add question
              </v-btn>
          </v-col>
          <v-col cols="12" md="2">
              <v-btn color="primary" @click="commitTest()">
                  Commit
              </v-btn>
          </v-col>
      </v-row>
  </v-container>
</template>

<script>
import QuestionDialog from '../dialogs/modals/QuestionDialog.vue'
import axios from 'axios'
import * as comm from '../configuration/communication.js'
export default {
  components: { QuestionDialog },
    name: "Test",
    props: ['courseId','testId'],
    data(){
        return{
            title: "",
            renderComponent: true,
            questions:[]
        }
    },
    created(){
        if(this.testId){
            //TODO: preuzmi test sa beka
        }
    },
    methods:{
        updateQuestion(index,newQuestion){
            this.questions[index] = newQuestion;
            
            console.log(this.questions)
        },
        commitTest(){
            let config = {
                headers: comm.getHeader()
            }
            axios.post(comm.protocol +'://' + comm.server + '/tests', {title: this.title, courseId: this.courseId, questions: this.questions},config)
            .then(response => {
              if(response.status==200){
                alert("uspesno kreiran test")
              }
            }).catch(() => {
              alert("greska")
            })
        },
        addQuestion(){
            this.questions.push({
                text: "New question",
                answers: [],
                isRandom: false,
                points: ""
            })
        },
        forceRerender() {
            // Removing my-component from the DOM
            this.renderComponent = false;

            this.$nextTick(() => {
            // Adding the component back in
            this.renderComponent = true;
        });
      }
    }
}
</script>

<style>

</style>