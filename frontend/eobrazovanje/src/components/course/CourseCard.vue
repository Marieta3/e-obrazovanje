<template>
  <v-card class="mx-auto" max-width="344" @click="enterCourse()" elevation="24">
    <v-img
      src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
      height="200px"
    ></v-img>

    <v-card-title>
      {{course.name}}
    </v-card-title>

    <v-card-subtitle>
      {{course.description}}
    </v-card-subtitle>
  </v-card>
</template>

<script>
  export default {
    props:['course'],
    data: () => ({
      show: false,
    }),
    methods:{
        enterCourse(){
            this.$router.push({name: 'Test', params: {courseId : this.course.id}})
        }
    }
  }
</script>