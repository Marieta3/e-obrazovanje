<template>
  <v-container>
      <v-row><v-btn @click="showTests()" :width="width" class="ma-2" :color="color">Tests</v-btn></v-row>
      <v-row><v-btn @click="showKnowledgeSpaces()" :width="width" class="mx-2" :color="color">Knowledge spaces</v-btn></v-row>
      <v-row><v-btn @click="showDomain()" :width="width" class="ma-2" :color="color">Domain</v-btn></v-row>
  </v-container>
</template>

<script>
export default {
    props: ['courseId'],
    data(){
        return{
            width:200,
            color:'success'
        }
    },
    methods:{
        showTests(){
            this.$router.push({name: 'Course.Tests', params: {courseId : this.courseId}})
        },
        showKnowledgeSpaces(){
            this.$router.push({name: 'Course.KnowledgeSpaceTable', params: {courseId : this.courseId}})
        },
        showDomain(){
            this.$router.push({name: 'Course.Domain', params: {courseId : this.courseId}})
        }
    }
}
</script>

<style>

</style>