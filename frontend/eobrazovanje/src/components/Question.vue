<template>
  <v-container>
      <v-row justify="center">
          <v-col cols="12" md="8" >
              <v-textarea
                filled
                v-model="question.text"
                label="Question"
                ></v-textarea>
          </v-col>
      </v-row>
      <v-row justify="center">
          <v-col cols="12" md="3" >
              <v-switch
                v-model="question.isRandom"
                :label="`randomize`"
                ></v-switch>
          </v-col>
           <v-col cols="12" md="3" >
              <v-text-field
                v-model="question.points"
                label="Points"
                filled
            ></v-text-field>
          </v-col>
      </v-row>
      <v-row v-for="(item,index) in question.answers" :key="index" justify="center">
          <v-col cols="12" md="8">
              <answer :oldAnswer="item" v-on:answerChanged="changeAnswer($event,index)"/>
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
              <v-btn color="primary" @click="commitQuestion()">
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
    props: ["oldQuestion"],
    data(){
        return {
            question : {
                text: "",
                answers: [],
                isRandom: false,
                points: ""
            }
        }
    },

    methods:{
        addAnswer(){
            this.question.answers.push({
                text : "",
                isCorrect: false
            })
        },
        changeAnswer(item, index){
            this.question.answers[index] = item;
        },
        commitQuestion(){

            this.$emit('commitQuestion',this.question)
        }
    },
    created(){
        this.question = this.oldQuestion
    },


}
</script>

<style>

</style>