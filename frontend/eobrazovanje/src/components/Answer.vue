<template>
  <v-container>
      <v-row>
          <v-col cols="12" md="3">
              <v-checkbox
                v-model="answer.correct"
                :label="`Correct?`"
                @change="emitChange()"
              ></v-checkbox>
          </v-col>
          <v-col cols="12" md="9">
              <v-text-field v-if="loggedUserType == userTypes.TEACHER"
                label="Answer"
                v-model="answer.text"
                filled
                @change="emitChange()"
            ></v-text-field>
            <p v-if="loggedUserType == userTypes.STUDENT">{{answer.text}}</p>
          </v-col>
      </v-row>
  </v-container>
</template>

<script>
import * as comm from '../configuration/communication.js'
export default {
    name: "Answer",
    props: ["currentAnswer"],
    data(){
        return{
            answer : {
                id: 0,
                text: "",
                imagePath: "",
                correct: false,
            },
            userTypes: comm.Role,
            loggedUserType: 0
        }
    },
    methods:{
        emitChange(){
            this.$emit('answerChanged',this.answer)
        },
    },
    created(){
        this.loggedUserType = comm.getRole()
        this.answer = this.currentAnswer
    }
}
</script>

<style>

</style>