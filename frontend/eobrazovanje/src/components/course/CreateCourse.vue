<template>
  <v-form>
    <v-container>
      <v-row justify="center">
        <v-col cols="12" md="8" >
          <v-text-field
            v-model="name"
            label="Name"
            required
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="12" md="8" >
         <v-combobox v-model="select" :items="allTeachers" label="Teacher"></v-combobox>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="12" md="8" >
          <v-textarea
            filled
            v-model="description"
            label="Description"
            ></v-textarea>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="12" sm="6" >
            <v-btn color="primary" @click="create()">Create</v-btn>
        </v-col>
       </v-row>
    </v-container>
  </v-form>
</template>

<script>
import axios from 'axios'
import * as comm from '../../configuration/communication.js'
export default {
    name:"CreateCourse",
    data () {
      return {
        name : "",
        description: "",
        select: "",
        allTeachers: []
      }
    },
    created(){
        this.getAllTeachers()
    },
    methods : {
        create(){
            let selectedTeacherId = this.select.split(" ")[0]
            let config = {
                headers: comm.getHeader()
            }
            axios.post(comm.protocol +'://' + comm.server + '/courses', {name: this.name, description: this.description, teacherId: selectedTeacherId}, config)
            .then(response => {
              if(response.status==200){
                alert("uspesno kreiran kurs")
              }
            }).catch(() => {
              alert("greska")
            })
        },
        getAllTeachers(){
            let config = {
                headers: comm.getHeader()
            }
            axios.get(comm.protocol +'://' + comm.server + '/users/teachers',config)
            .then(response => {
              if(response.status==200){
                  console.log(response.data)
                  this.prepareDataFromResponse(response.data)
              }
            }).catch(() => {
              alert("greska")
            })
        },
        prepareDataFromResponse(items){
            let result = []
            for(let item of items){
                result.push(item.id + " " + item.firstName + " " + item.lastName )
            }
            this.allTeachers = result
        }
    }
}
</script>

<style>

</style>