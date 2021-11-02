<template>
  <v-container>
    <v-row align="center" justify="center">
      <v-col cols="12" md="8">
        <log-in/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import LogIn from './LogIn.vue'
  export default {
  components: { LogIn },
    
  }
</script>
