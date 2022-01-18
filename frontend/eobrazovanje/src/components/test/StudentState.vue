<template>
  <div>
      Student state

      <div v-for="d in domainProblems" :key="d.id">
          <p>{{d.studentKnow}} -- {{d.title}}</p>
      </div>
  </div>
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
    }
}
</script>

<style>

</style>