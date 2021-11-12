<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      fullscreen
      hide-overlay
      transition="dialog-top-transition"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-card class="d-flex justify-start" width="300px" flat tile elevation="12" color="success" dark v-bind="attrs" v-on="on">
            <p class="ml-2 mt-2">{{index}}</p>
            <p class="pl-6 mt-2">{{currentQuestion.text}}</p>
        </v-card>
      </template>
      <v-card>
        <v-toolbar dark color="primary" >
          <v-btn icon dark @click="dialog = false" >
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>Question {{index}}</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
        <question :currentQuestion="currentQuestion" :key="changeIndicator" v-on:commitQuestion="commit($event)"/>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import Question from '../../components/Question.vue'
  export default {
  components: { Question },
    name: "QuestionDialog",
    props: ["index", "currentQuestion"],
    data () {
      return {
        changeIndicator: 0,
        question : {},
        dialog: false,
      }
    },
    methods:{
        commit(newQuestion){
            console.log("komitujem pitanje "+ this.index);
            this.$emit('commitedQuestion',newQuestion)
            this.dialog = false
            this.changeIndicator += 1;
        },
    },
    
  }
</script>