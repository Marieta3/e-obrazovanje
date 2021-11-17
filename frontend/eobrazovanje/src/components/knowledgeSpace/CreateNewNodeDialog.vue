<template>
  <v-dialog
        transition="dialog-top-transition" max-width="600">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color="primary"
            v-bind="attrs"
            v-on="on"
            @click="resetForm"
          >Create node</v-btn>
        </template>
        <template v-slot:default="dialog">
          <v-card>
            <v-toolbar color="primary" dark>
                Node
            </v-toolbar>
            <v-card-text>
              <node-form v-on:dataChanged="node = $event"/>
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
import NodeForm from './NodeForm.vue'
export default {
    components:{
        NodeForm
    },
    data(){
        return{
            node: {
                title: "",
                description: ""
            }
        }
    },
    methods:{
      confirm(){
        this.$emit('nodeCreated',this.node)
      },
      resetForm(){
        this.node.title = "";
        this.node.description = "";
      }
    }
}
</script>

<sty
NodeFormle>

</style>