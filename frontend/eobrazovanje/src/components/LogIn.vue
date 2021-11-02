<template>
  <v-form>
    <v-container>
      <v-row justify="center">
        <v-col cols="12" md="8" >
          <v-text-field
            v-model="username"
            label="Username"
            required
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="12" md="8" >
         <v-text-field
            v-model="password"
            :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
            :type="show ? 'text' : 'password'"
            label="Password"
            @click:append="show = !show"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="12" sm="6" >
            <v-btn color="primary" @click="login()">Log in</v-btn>
        </v-col>
       </v-row>
    </v-container>
  </v-form>
</template>

<script>
import axios from 'axios'
import * as comm from '../configuration/communication.js'
export default {
    name: "LogIn",
    data() {
        return {
            username : "",
            password : "",
            show : false,
        }
    },
    methods:{
        login(){
            axios.post(comm.protocol +'://' + comm.server + '/auth/login', {username: this.username, password: this.password})
            .then(response => {
              if(response.status==200){
                alert("uspesno se ulogovao")
                comm.setJWTToken(response.data.accessToken);
              }
            }).catch(() => {
              alert("greska")
            })
        }
    }
}
</script>

<style>

</style>