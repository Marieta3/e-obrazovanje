<template>
   <div id="nav">
      <v-container>
        <v-row align="center" justify="center">
          <v-col cols="12" sm="4"><v-spacer/></v-col>
          <v-col cols="12" sm="4">
                <router-link :to="{ name: 'Courses'}">Courses</router-link> 
          </v-col>
          <v-col cols="12" sm="4">
              <v-btn color="success" @click="logOut()">LogOut</v-btn>
          </v-col>
          <v-col cols="12" sm="4"/>
        </v-row>
      </v-container>
    </div>
</template>

<script>
import * as comm from '../configuration/communication.js'
export default {
    name: "NavBar",
    data(){
      return {
        userTypes: comm.Role
      }
    },
    methods: {
      logOut(){
        comm.logOut();
        this.$router.push({name: 'Home'})
      }

    }

}
</script>

<style>

</style>