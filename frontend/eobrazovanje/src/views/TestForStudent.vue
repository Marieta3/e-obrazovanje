<template> 
    <v-container>
        <v-row justify="center">
            <v-col v-if="!testStarted">
                <v-btn color="success"  @click="startTest()" >Start test</v-btn>
            </v-col>
            <v-col  v-else>
                <v-container>
                    <v-row>
                        <v-col v-if="renderComponent">
                            <question :currentQuestion="question" v-on:questionChanged="updateMyAnswers($event)"/>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col>
                            <v-btn color="success" @click="answer()">Confirm answer</v-btn>
                        </v-col>
                    </v-row>
                </v-container>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import axios from 'axios'
    import * as comm from '../configuration/communication.js'
    import Question from '../components/Question.vue'
    export default {
        props: ['testId','courseId'],
        components:{
            Question
        },
        data() {
            return {
                currentStep: 0,
                questions: [],
                question: {},
                steps: 0,
                testStarted: false,
                startTime: null,
                testResultId: null,
                renderComponent: true,
                answeredIndex: -1,
                generatedTest: false
            }
        },
        methods:{
            getTestQuestions(){
                let config = {headers: comm.getHeader()}
                axios.get(comm.protocol +'://' + comm.server + '/tests/'+this.testId,config)
                .then(response => {
                if(response.status==200){
                    this.questions = response.data.questions
                    this.question = this.questions[this.answeredIndex+1]
                    this.steps = this.questions.length
                    this.testStarted = true
                    this.startTime = new Date().getTime()
                }
                }).catch(() => {
                alert("greska")
                })
            },
            updateMyAnswers(updatedQuestion){
                this.question = updatedQuestion
            },
            finishTest(){
                let myAnswers = this.getMyAnswers()
                let endTime = new Date().getTime()
                let config = { headers: comm.getHeader() }
                let data = {startTime: this.startTime, endTime: endTime, answerIDs: myAnswers, testID: this.testId}
                axios.post(comm.protocol +'://' + comm.server + '/test-results/manually-created', data ,config)
                .then(response => {
                if(response.status==200){
                    alert("uspesno zavrsen test")
                }
                }).catch(() => {
                alert("greska")
                })
            },
            getMyAnswers(){
                let result = []
                for(let q of this.questions){
                    for(let a of this.getSelectedAnswersForQuestion(q))
                        result.push(a)
                }
                return result
            },
            getSelectedAnswersForQuestion(question){
                let result = [] 
                for(let answer of question.answers){
                    if (answer.correct == true){
                        result.push(answer.id)                           
                    }
                }
                return result
            },
            getFirstQuestion(){
                let data = {testId: this.testId}
                let config = { headers: comm.getHeader() }
                axios.post(comm.protocol +'://' + comm.server + '/test-results', data ,config)
                    .then(response => {
                        if(response.status==200){
                            this.question = response.data.question
                            this.testResultId = response.data.testResultId
                            this.testStarted = true
                        }
                    }).catch(() => {
                        alert("greska")
                    })
            },
            answer(){
                if(this.generatedTest){
                    this.sendAnswer()
                }else{
                    this.answeredIndex += 1
                    if(this.answeredIndex == this.questions.length-1){
                        alert("Zavrsen test!")
                        console.log(this.questions)
                        this.finishTest();
                    }else{
                        this.question=this.questions[this.answeredIndex+1]
                        this.forceRerender()
                    }
                }
            },
            sendAnswer() {
                let data={ testId: this.testId, testResultId: this.testResultId, answerIds: this.getSelectedAnswersForQuestion(this.question) }
                let config={ headers: comm.getHeader() }
                axios.post(comm.protocol+'://'+comm.server+'/test-results', data, config)
                    .then(response => {
                        if(response.status==200) {
                        this.question=response.data.question
                        this.forceRerender()
                        }
                    }).catch(() => {
                        alert("greska")
                    })
            },
            startTest(){
                this.getActiveKnowledgeSpaceTypeAndQuestions()                
            },
            getActiveKnowledgeSpaceTypeAndQuestions(){
                let config={ headers: comm.getHeader() }
                axios.get(comm.protocol+'://'+comm.server+'/knowledge-spaces/course/'+this.courseId+'/active', config)
                    .then(response => {
                        if(response.status==200) {
                            this.generatedTest = response.data.type != 'MANUAL'
                            console.log(this.generatedTest)
                            if(this.generatedTest){
                                this.getFirstQuestion()
                            }else{
                                this.getTestQuestions()
                            } 
                        }
                    }).catch(() => {
                        alert("greska")
                    })
            },
            forceRerender() {
            // Removing my-component from the DOM
            this.renderComponent = false;

            this.$nextTick(() => {
            // Adding the component back in
                this.renderComponent = true;
            });
        },
        }
    }
    

    
</script>