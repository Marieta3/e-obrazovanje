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
import CourseCard from '../components/course/CourseCard.vue'
export default {
    components: { CourseCard },
    name: "Courses",
    data(){
        return {
            courses:[
                {name: "Course 1", description: "Desc 1"},
                {name: "Course 2", description: "Desc 2"},
                {name: "Course 3", description: "Desc 3"},
                {name: "Course 4", description: "Desc 4"},
                {name: "Course 5", description: "Desc 5"},
                {name: "Course 6", description: "Desc 6"},
                {name: "Course 7", description: "Desc 7"}
            ]
        }
    }
}
</script>

<style>

</style>