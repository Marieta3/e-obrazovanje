<template>
   <div id="nav">
      <v-container v-if="isUserLogged">
        <v-row align="center" justify="center">
          <v-col cols="12" sm="4"><v-spacer/></v-col>
          <v-col cols="12" sm="4">
                <router-link :to="{ name: 'Courses'}">Courses</router-link> 
          </v-col>
          <v-col cols="12" sm="4">
            <v-menu offset-y bottom left>
              <template v-slot:activator="{ on, attrs }">
                <v-btn icon v-bind="attrs" v-on="on">
                  <v-icon>mdi-dots-vertical</v-icon>
                </v-btn>
              </template>
              <v-list>
                <v-list-item>
                  <v-list-item-title @click="logOut()"><a>Log out</a></v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
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
        userTypes: comm.Role,
         isUserLogged: comm.getJWTToken() != null,
      }
    },
    mounted(){
       this.$root.$on('loggedUser', () => {
        this.isUserLogged = comm.getJWTToken() != null;
      })
    },
    methods: {
      logOut(){
        comm.logOut();
        this.isUserLogged = false;
        this.$router.push({name: 'Home'})
      }

    }

}
</script>

<style>

</style>