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
                class="mr-2"
                @click="editItem(item)"
            >
                mdi-pencil
            </v-icon>
            <v-icon
                small
                @click="deleteItem(item)"
            >
                mdi-delete
            </v-icon>
            </template>
            <template v-slot:no-data>
            <v-btn
                color="primary"
                @click="getDomainProblemsForDomain"
            >
                Reset
            </v-btn>
            </template>
        </v-data-table>
        <domain-problem-new-edit-dialog :item="selectedItem" v-on:commited="addNewNode($event)"/>
    </div>
</template>

<script>
import DomainProblemNewEditDialog from './DomainProblemNewEditDialog.vue'
import * as comm from '../../configuration/communication.js'
import axios from 'axios'
  export default {
  components: { DomainProblemNewEditDialog },
    data: () => ({
      selectedItem: null,
      headers: [
        { text: 'Id', value: 'id'},
        { text: 'Title', value: 'title' },
        { text: 'Description', value: 'description', sortable: false },
        { text:'',value:'actions',sortable: false}
      ],
      domainProblems: [],
    }),




    created () {
      this.getDomainProblemsForDomain()
    },

    methods: {
      
    editItem(item){
      this.selectedItem=item;
    },
    
    deleteItem(item){
      alert(item.id)
    },
    addNewNode(item){
      this.domainProblems.push(item)
    },
    getDomainProblemsForDomain(){
      axios.get(comm.protocol +'://' + comm.server + '/courses/1/domain',{headers: comm.getHeader()})
          .then(response => {
            if(response.status==200){
                console.log(response.data);
                this.domainProblems = response.data;
            }
          }).catch(() => {
            alert("greska")
          })
      }
    }
  }
</script>