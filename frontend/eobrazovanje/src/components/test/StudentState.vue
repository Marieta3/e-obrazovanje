<template>
  <span>
      <div>
        <h1>Student state</h1>
      </div>
      <div>
        <h3>Oblast koje student zna:</h3>
        <div v-for="d in getCorrectDomainProblems(true)" :key="d.id">
            <p>{{d.title}}</p>
        </div>
      </div>
      <div>
        <h3>Oblasti koje student ne zna:</h3>
        <div v-for="d in getCorrectDomainProblems(false)" :key="d.id">
            <p>{{d.title}}</p>
        </div>
    </div>
  </span>
</template>

<script>

import * as comm from '../../configuration/communication.js'
import axios from 'axios'

export default {
    props:["id"],
    data(){
        return {
            domainProblems: []
        }
    },
    created(){
         axios.get(comm.protocol +'://' + comm.server + '/test-results/' + this.id + '/state',{headers: comm.getHeader()})
                .then(response => {
                    if(response.status==200){
                        console.log(response.data)
                        this.domainProblems = response.data
                    }
                }).catch(()=>{
                    alert("greska")
                })
    },
    methods: {
        getCorrectDomainProblems(correct){
            let result = []
            for(let dp of this.domainProblems){
                if(dp.studentKnow== correct)
                    result.push(dp)
            }
            return result;
        }
    }
}
</script>

<style>

</style>