<template>
   <div id="nav">
      <v-container>
        <v-row align="center" justify="center">
          <v-col cols="12" sm="4"><v-spacer/></v-col>
          <v-col cols="12" sm="4">
                <router-link :to="{ name: 'Courses'}">Courses</router-link> 
          </v-col>
          <v-col cols="12" sm="4"/>
        </v-row>
      </v-container>
    </div>
</template>

<script>
export default {
    name: "NavBar",
    data(){
      return {
      }
    },
}
</script>

<style>

</style>