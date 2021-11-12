<template>    
    <v-stepper v-model="currentStep">
        <v-stepper-header>
            <template v-for="n in steps">
            <v-stepper-step
                :key="`${n}-step`"
                :complete="currentStep > n"
                :step="n"
                editable
            >
                Question {{ n }}
            </v-stepper-step>

            <v-divider
                v-if="n !== steps"
                :key="n"
            ></v-divider>
            </template>
        </v-stepper-header>
           
        <v-stepper-items class="text-center">
            <v-stepper-content v-for="(q,index) in questions" :key="q.id" :step="index+1">
                <v-container>
                    <v-row>
                        <v-col>
                            <question :currentQuestion="q" v-on:questionChanged="updateMyAnswers(index,$event)"/>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col>
                            <v-btn color="success" @click="finishTest()">Finish test</v-btn>
                        </v-col>
                    </v-row>
                </v-container>
                
            </v-stepper-content>
        </v-stepper-items>
    </v-stepper>
</template>

<script>
    import axios from 'axios'
    import * as comm from '../configuration/communication.js'
    import Question from '../components/Question.vue'
    export default {
        props: ['testId'],
        components:{
            Question
        },
        data() {
            return {
                currentStep: 0,
                questions: [],
                steps: 0
            }
        },
        created(){
            this.getTestQuestions()
        },
        methods:{
            getTestQuestions(){
                let config = {headers: comm.getHeader()}
                axios.get(comm.protocol +'://' + comm.server + '/tests/'+this.testId,config)
                .then(response => {
                if(response.status==200){
                    console.log(response.data)
                    this.questions = response.data.questions
                    this.steps = this.questions.length
                }
                }).catch(() => {
                alert("greska")
                })
            },
            updateMyAnswers(index, updatedQuestion){
                this.questions[index] = updatedQuestion
            },
            finishTest(){
                console.log(this.questions)
            }
        }
    }
    
</script>