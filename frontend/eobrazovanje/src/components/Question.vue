<template>
  <v-container>
      <v-row justify="center">
          <v-col cols="12" md="8" >
              <v-textarea
                filled
                v-model="question.text"
                :disabled="!hasAnyRole(userTypes.TEACHER)"
                label="Question"
                placeholder="New question"
                ></v-textarea>
          </v-col>
      </v-row>
      <v-row justify="center">
          <v-col v-if="hasAnyRole(userTypes.TEACHER)" cols="12" md="3" >
              <v-switch
                v-model="question.randomized"
                :label="`randomize`"
                ></v-switch>
          </v-col>
          <v-col cols="12" md="3" v-else/>
           <v-col cols="12" md="3" >
              <v-text-field
                v-model="question.points"
                label="Points"
                :disabled="!hasAnyRole(userTypes.TEACHER)"
                filled
            ></v-text-field>
          </v-col>
      </v-row>
      <!-- TODO ako nije profesor i selektovan je randomize onda promeniti redosled odgovora -->
      <v-row v-for="(item,index) in question.answers" :key="index" justify="center">
          <v-col cols="12" md="8">
              <answer :currentAnswer="item" v-on:answerChanged="changeAnswer($event,index)"/>
          </v-col>
      </v-row>
      <v-row v-if="hasAnyRole(userTypes.TEACHER)">
        <v-col cols="12" md="2">
            <v-btn color="primary" @click="addAnswer()">
                Add Answer
            </v-btn>
        </v-col>
      </v-row>
  </v-container>
</template>

<script>
import Answer from './Answer.vue'
import * as comm from '../configuration/communication.js'
export default {
  components: { Answer },
    name: "Question",
    props: ["currentQuestion"],
    data(){
        return {
            question : {
                text: "",
                answers: [],
                randomized: false,
                points: ""
            },
            userTypes: comm.Role,
            loggedUserType: 0
        }
    },

    methods:{
        addAnswer(){
            this.question.answers.push({
                text : "",
                correct: false
            })
        },
        changeAnswer(item, index){
            this.question.answers[index] = item;
            this.$emit('questionChanged',this.question)
        },
        commitQuestion(){
            this.$emit('commitQuestion',this.question)
        },
        hasAnyRole(... roles){
            for(let role of roles){
                if(role == this.loggedUserType){
                    return true
                }
            }
            return false
        }
    },
    created(){
        this.loggedUserType = comm.getRole()
        this.question = this.currentQuestion
    },
    watch:{
        currentQuestion: function(){
            this.question = this.currentQuestion
        }
    }


}
</script>

<style>

</style>