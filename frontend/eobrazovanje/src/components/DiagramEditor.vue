<template>
<div>
  <VueDiagramEditor
    v-on:created-link="createLink($event)"
    ref="diagram"
    :node-color="nodeColor"
    :node-pulsable="nodePulsable"
  >
    <pre slot="node" slot-scope="{node}">{{ format(node) }}</pre>
  </VueDiagramEditor>
  <v-btn @click="addNewNode()">Dodaj node</v-btn>
</div>
</template>

<script>
import VueDiagramEditor from 'vue-diagram-editor';
import 'vue-diagram-editor/dist/vue-diagram-editor.css';
export default {
  name: 'simple-example',
  components: {
    VueDiagramEditor
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
        //Tekst koji se prikazuje unutar node
      return node.title;
    },
    nodeColor() {
        
      return '#00f';
    },

    nodePulsable(node) {
      return node.coordinates.y > 200;
    },
    createLink(data){
        data.id = 15
    },
    addNewNode(){
        let id = this.id
        let newNode = {
            id: 'node-'+ id,
            title: 'My node '+id,
            size: {
            width: 200,
            height: 220
            },
            coordinates: {
            x: 300,
            y: 200
            },
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