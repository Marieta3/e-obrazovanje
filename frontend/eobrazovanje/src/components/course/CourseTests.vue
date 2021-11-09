<template>
  <v-container>
      <v-row  v-for="t in tests" :key="t.id" justify="center" class="pt-3">
        <v-btn @click="redirectToTest(t.id)">
            {{t.title}}
        </v-btn>
      </v-row>
      <v-row>
          <v-col cols="12" sm="4">
              <v-btn color="success" @click="redirectToCreateTestPage()">Create new</v-btn>
          </v-col>
      </v-row>
  </v-container>
</template>

<script>
import axios from 'axios'
import * as comm from '../../configuration/communication.js'
export default {
    props:['courseId'],
    data(){
        return{
            tests: []
        }
    },
    created(){
        this.getCourseTests();
    },
    methods:{
        getCourseTests(){
            let config = {
                headers: comm.getHeader()
            }
            axios.get(comm.protocol +'://' + comm.server + '/courses/'+this.courseId+'/tests',config)
            .then(response => {
              if(response.status==200){
                console.log(response.data)
                this.tests = response.data
              }
            }).catch(() => {
              alert("greska")
            })
        },
        redirectToTest(testId){
             this.$router.push({name: 'Test', params: {courseId : this.courseId, testId: testId}})
        },
        redirectToCreateTestPage(){
            this.$router.push({name: 'NewTest', params: {courseId : this.courseId}})
        }
    }
}
</script>

<style>

</style>