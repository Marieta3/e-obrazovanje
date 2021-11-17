<template>
  <v-container>
    <v-row>
      <v-col>
        <VueDiagramEditor
          v-on:created-link="createLink($event)"
          ref="diagram"
          :node-color="nodeColor"
          :node-pulsable="nodePulsable"
        >
          <pre slot="node" slot-scope="{node}">{{ format(node) }}</pre>
        </VueDiagramEditor>
      </v-col>
    </v-row>
    <v-row>
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
export default {
  name: 'simple-example',
  components: {
    VueDiagramEditor,
    CreateNewNodeDialog
  },
  data: () => ({
     nodes:{},
     id: 1
  }),
  mounted() {
    this.init();
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

    nodePulsable(node) {
      return node.coordinates.y > 200;
    },
    createLink(data){
        data.id = "" + data.start_id 
    },
    addNewNode(node){
        let id = this.id
        let newNode = {
            id: 'node-'+ id,
            title: node.title,
            data : node.description,
            portsIn: {
            default: 'in'
            },
            portsOut: {
                default: 'out'
            }
        }
        this.$refs.diagram.addNode(newNode)
        this.id += 1 
        console.log(this.$refs.diagram.serialize())
    }
  }
};
</script>

<style>

</style>