<template>
  <v-container>
      <v-row justify="center">
          <v-col cols="12" md="8" >
              <v-textarea
                filled
                v-model="text"
                label="Question"
                ></v-textarea>
          </v-col>
      </v-row>
      <v-row v-for="(item,index) in answers" :key="index" justify="center">
          <v-col cols="12" md="8">
              <answer v-on:answerChanged="changeAnswer($event,index)"/>
          </v-col>
      </v-row>
      <v-row>
          <v-col cols="12" md="4" />
          <v-col cols="12" md="2">
              <v-btn color="primary" @click="addAnswer()">
                  Add answer
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
import Answer from './Answer.vue'
export default {
  components: { Answer },
    name: "Question",
    data(){
        return {
            text: "",
            answers: [],
        }
    },

    methods:{
        addAnswer(){
            this.answers.push({
                text : "",
                isCorrect: false
            })
        },
        changeAnswer(item, index){
            this.answers[index] = item;
        },
        commitTest(){
            console.log(this.answers)
        }
    }

}
</script>

<style>

</style>