<template>
  <v-container>
    <v-row>
        <question ref="questionComponent" :currentQuestion="currentQuestion" v-on:questionChanged="changeQuestion($event)"/>
    </v-row>
    <v-row>
        <v-col cols="12" md="4" />
        <v-col cols="12" md="2">
            <v-btn color="primary" @click="addAnswer()">
                Add question
            </v-btn>
        </v-col>
        <v-col cols="12" md="2">
            <v-btn color="primary" @click="commitQuestion()">
                Commit
            </v-btn>
        </v-col>
      </v-row>
  </v-container>
</template>

<script>
import Question from '../components/Question.vue'
export default {
    props:['question'],
    data(){
        return{
            currentQuestion: this.question 
        }
    },
    components: {
        Question
    },
    methods : {
        changeQuestion(newQuestion){
            this.currentQuestion = newQuestion
        },
        commitTest(){
            let config = {headers: comm.getHeader()}
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
    }
}
</script>

<style>

</style>