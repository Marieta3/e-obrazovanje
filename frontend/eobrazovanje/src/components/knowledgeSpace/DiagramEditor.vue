<template>
  <v-container>
    <v-row>
      <v-col>
        <VueDiagramEditor
          v-on:created-link="createLink($event)"
          ref="diagram"
          :node-color="nodeColor"
          :node-pulsable="nodePulsable"
          :nodeDeletable="deletable"
        >
          <pre slot="node" slot-scope="{node}">{{ format(node) }}</pre>
        </VueDiagramEditor>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-btn @click="submit()">Submit</v-btn>
      </v-col>
      <v-col>
        <create-new-node-dialog v-on:nodeCreated="addNewNode($event)"/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import VueDiagramEditor from 'vue-diagram-editor';
import 'vue-diagram-editor/dist/vue-diagram-editor.css';
import CreateNewNodeDialog from './CreateNewNodeDialog'
import axios from 'axios'
import * as comm from '../../configuration/communication.js'
export default {
  props:['knowledgeSpaceId'],
  name: 'simple-example',
  components: {
    VueDiagramEditor,
    CreateNewNodeDialog
  },
  data: () => ({
     nodes: {},
     id: 1
  }),
  mounted() {
    this.init();
      if(this.knowledgeSpaceId != 0){
      this.getKnowledgeSpace()
    }
  },
  methods: {
    init() {
      this.$refs.diagram.setModel({
        nodes: this.nodes,
        links: this.links
      });
    },
    format(node) {
      return node.data;
    },
    nodeColor() {
      return '#00f';
    },
    deletable(){
      return false
    },

    nodePulsable(node) {
      console.log(node.coordinates.y > 200)
      return false
    },
    createLink(data){
      console.log(data.id)
    },
    addNewNode(node){
        let id = this.id
        let newNode = {
            id: 'node-'+ id,
            title: node.title,
            data : node.description,
            portsIn: {
              port: 'in',
            },
            portsOut: {
                port: 'out'
            }
        }
        this.$refs.diagram.addNode(newNode)
        this.id += 1    
    },
    getKnowledgeSpace(){
      console.log("usao")
      let config = { headers: comm.getHeader() }
      axios.get(comm.protocol +'://' + comm.server + '/knowledge-spaces/'+this.knowledgeSpaceId, config)
                .then(response => {
                if(response.status==200){
                  console.log(response.data)
                    this.$refs.diagram.setModel(response.data)
                }
                }).catch(() => {
                alert("greska")
                })
    },
    submit(){
      let data = this.$refs.diagram.serialize()
      console.log(data)
      let config = { headers: comm.getHeader() }
      axios.post(comm.protocol +'://' + comm.server + '/knowledge-spaces', data ,config)
                .then(response => {
                if(response.status==200){
                    alert("uspesno zavrsen prostor znanja")
                }
                }).catch(() => {
                alert("greska")
                })
    }
  },
   watch:{
        knowledgeSpaceId: function(){
            this.init()
        }
    }
};
</script>

<style>

</style>