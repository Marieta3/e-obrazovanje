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
              <v-text-field
                label="Answer"
                v-model="answer.text"
                filled
                @change="emitChange()"
            ></v-text-field>
          </v-col>
      </v-row>
  </v-container>
</template>

<script>
export default {
    name: "Answer",
    props: ["oldAnswer"],
    data(){
        return{
            answer : {
                text: "",
                imagePath: "",
                correct: false,
            }
        }
    },
    methods:{
        emitChange(){
            this.$emit('answerChanged',this.answer)
        }
    },
    created(){
        this.answer = this.oldAnswer
    }
}
</script>

<style>

</style>