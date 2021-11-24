<template>
  <v-container>
      <v-row  v-for="t in tests" :key="t.testId" justify="center" class="pt-3">
        <v-btn @click="redirectToTest(t.testId)">
            {{t.title}}
        </v-btn>
      </v-row>
      <v-row v-if="hasAnyRole(userTypes.TEACHER)">
          <v-col cols="12" sm="4">
              <v-btn color="success" @click="redirectToCreateTestPage()">Create new test</v-btn>
          </v-col>
          <v-col cols="12" sm="4">
              <v-btn color="success" @click="redirectToDiagramEditor()">Knowledge space</v-btn>
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
            tests: [],
            userTypes: comm.Role,
        }
    },
    created(){
        if(comm.hasRole("ROLE_TEACHER")){
            this.getAllCourseTests();
        } else if(comm.hasRole("ROLE_STUDENT")){
            this.getTestsForStudent();
        }
    },
    methods:{
        getAllCourseTests(){
            let config = {headers: comm.getHeader()}
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
        getTestsForStudent(){
            let config = {headers: comm.getHeader()}
            axios.get(comm.protocol +'://' + comm.server + '/courses/'+this.courseId+'/tests/available',config)
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
            if(comm.hasRole("ROLE_STUDENT"))
                this.$router.push({name: 'StudentTest', params: {testId: testId}})
            else    
                this.$router.push({name: 'Test', params: {courseId : this.courseId, testId: testId}})
            
        },
        redirectToCreateTestPage(){
            this.$router.push({name: 'NewTest', params: {courseId : this.courseId}})
        },
        hasAnyRole(... roles){
            return comm.hasAnyRole(roles)
        },
        redirectToDiagramEditor(){
            //TODO: srediti knowledgeSpaceId
            this.$router.push({name: 'Diagram', params: {knowledgeSpaceId : 1}})
        }
    }
}
</script>

<style>

</style>