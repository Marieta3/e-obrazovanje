<template>
  <v-data-table
    :headers="headers"
    :items="knowledgeSpaces"
    class="elevation-1"
  >
  <template v-slot:item.createdAt="{ item }">
      {{item.createdAt | dateFormat("DD-MM-YYYY HH:mm")}}
  </template>
    <template v-slot:item.show="{ item }">
      <v-chip
        color='warning'
        dark
        @click="show(item.id)"
      >
        Show
      </v-chip>
    </template>
  </v-data-table>
</template>

<script>
  import axios from 'axios'
  import moment from 'moment'
  import * as comm from '../../configuration/communication.js'
  export default {
    props: ['courseId'],
    data () {
      return {
        headers: [
          {
            text: 'Created date (DD-MM-YYYY HH:mm)',
            align: 'start',
            value: 'createdAt',
          },
          { text: 'Type', value: 'type' },
          { text: '', value: 'show', sortable: false },
          { text: 'Is active', value: 'active' },
        ],
        knowledgeSpaces: [],
      }
    },
    created(){
        this.getKnowledgeSpacesForCurrentCourse();
    },
    methods: {
        getKnowledgeSpacesForCurrentCourse(){
            axios.get(comm.protocol +'://' + comm.server + '/courses/1/knowledge-spaces/description',{headers: comm.getHeader()})
            .then(response => {
              if(response.status==200){
                  console.log(response.data);
                  this.knowledgeSpaces = response.data;
              }
            }).catch(() => {
              alert("greska")
            })
        },
        show(knowledgeSpaceId){
            this.$router.push({name: 'Diagram', params: {knowledgeSpaceId : knowledgeSpaceId}})
        },
        showDate(date){
            let localDate = new Date(date)
            
            return localDate.getFullYear() + '-' + ((localDate.getMonth() < 9) ? '0' : '') + (localDate.getMonth() + 1) + '-' +
                ((localDate.getDate() < 10) ? '0' : '') + localDate.getDate();
        }
    },
    filters:{
        dateFormat: function(value, dateFormat){
            if (!value || !dateFormat) return ''
            value = new Date(value)
            return moment(value).format(dateFormat.toString())
        }
    }
  }
</script>

<style>

</style>