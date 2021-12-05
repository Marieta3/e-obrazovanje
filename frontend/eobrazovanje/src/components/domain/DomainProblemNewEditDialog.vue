<template>
  <v-dialog
        transition="dialog-top-transition" max-width="600">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color="primary"
            v-bind="attrs"
            v-on="on"
            @click="resetForm"
          >Create new domain problem</v-btn>
        </template>
        <template v-slot:default="dialog">
          <v-card>
            <v-toolbar color="primary" dark>
                Domain problem
            </v-toolbar>
            <v-card-text>
              <domain-problem-form v-on:dataChanged="node = $event"/>
            </v-card-text>
            <v-card-actions class="justify-space-around">
              <v-btn
                color="success"
                @click="confirm(); dialog.value = false"
              >Confirm</v-btn>
              <v-btn
                ref="btnClose"
                text
                @click="dialog.value = false"
              >Close</v-btn>
            </v-card-actions>
          </v-card>
        </template>
      </v-dialog>
</template>

<script>
import DomainProblemForm from './DomainProblemForm.vue'
export default {
    components:{
        DomainProblemForm
    },
    data(){
        return{
            node: {
                id : "",
                title: "",
                description: ""
            }
        }
    },
    methods:{
      confirm(){
        console.log("confirmed")
        this.$emit('commited',this.node)
      },
      resetForm(){
        this.node.title = "";
        this.node.description = "";
      }
    },
}
</script>