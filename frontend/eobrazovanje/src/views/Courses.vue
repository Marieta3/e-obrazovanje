<template>
    <v-container>
        <v-row>
            <v-col cols="12" sm="3" v-for="c in courses" :key="c.id">
                <course-card :course="c"/>    
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import axios from 'axios'
import CourseCard from '../components/course/CourseCard.vue'
import * as comm from '../configuration/communication.js'
export default {
    components: { CourseCard },
    name: "Courses",
    data(){
        return {
            courses: []
        }
    },
    created(){
        this.getMyCourses();
    },
    methods:{
        getMyCourses(){
             let config = {
                headers: comm.getHeader()
            }
            axios.get(comm.protocol +'://' + comm.server + '/courses/my',config)
            .then(response => {
              if(response.status==200){
                this.courses = response.data
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