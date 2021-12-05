<template>
    <div>
        <v-data-table
            :headers="headers"
            :items="domainProblems"
            class="elevation-1"
        >
            <template v-slot:item.actions="{ item }">
            <v-icon
                small
                color="error"
                :disabled="domainExists"
                @click="deleteItem(item)"
            >
                mdi-delete
            </v-icon>
            </template>
        </v-data-table>
        <domain-problem-new-edit-dialog v-if="!domainExists" v-on:commited="addNewNode($event)"/>
        <v-btn color="warning" v-if="!domainExists" @click="createDomain()">Save</v-btn>
    </div>
</template>

<script>
import DomainProblemNewEditDialog from './DomainProblemNewEditDialog.vue'
import * as comm from '../../configuration/communication.js'
import axios from 'axios'
  export default {
  components: { DomainProblemNewEditDialog },
    props: ['courseId'],
    data: () => ({
      selectedItem: null,
      isEdit: false,
      headers: [
        { text: 'Title', value: 'title' },
        { text: 'Description', value: 'description', sortable: false },
        { text:'',value:'actions',sortable: false}
      ],
      domainProblems: [],
      domainExists: false
    }),


    created () {
      this.getDomainProblemsForDomain()
    },

    methods: {
      
    addNewNode(item){
      let copiedNode = Object.assign({}, item);
      this.domainProblems.push(copiedNode)
    },
    createDomain(){
      console.log(this.domainProblems);
    },
    getDomainProblemsForDomain(){
      axios.get(comm.protocol +'://' + comm.server + '/courses/'+this.courseId+'/domain',{headers: comm.getHeader()})
          .then(response => {
            if(response.status==200){
                console.log(response.data);
                this.domainProblems = response.data;
                this.domainExists = true
            }
          }).catch(response => {
            if(response.status == 404){
              this.domainExists = false
            }
            //alert("greska")
          })
      },
       deleteItem(item){
        this.domainProblems.splice(this.domainProblems.indexOf(item), 1)
        this.domainProblems = [...this.domainProblems]
      },
    }
  }
</script>