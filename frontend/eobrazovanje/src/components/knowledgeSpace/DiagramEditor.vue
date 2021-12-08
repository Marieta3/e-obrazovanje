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
        <v-btn @click="submit()" color="success">Submit</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import VueDiagramEditor from 'vue-diagram-editor';
import 'vue-diagram-editor/dist/vue-diagram-editor.css';
import axios from 'axios'
import * as comm from '../../configuration/communication.js'
export default {
  props:['knowledgeSpaceId','courseId'],
  name: 'simple-example',
  components: {
    VueDiagramEditor,
  },
  data: () => ({
     nodes: {},
     id: 1
  }),
  mounted() {
    this.init();
    if(this.knowledgeSpaceId)
      this.getKnowledgeSpace() 
    else
      this.getDomainProblems(); 
  },
  methods: {
    init() {
      this.$refs.diagram.setModel({
        nodes: this.nodes,
        links: this.links
      });
    },
    format(node) {
      return node.data.description;
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
    addNewNode(node, cords){
        let newNode = {
            id: node.id,
            title: node.title,
            data :{domainProblemId: node.id, description: node.description},
            coordinates: cords,
            portsIn: {
              port: 'in',
            },
            portsOut: {
                port: 'out'
            }
        }
        this.$refs.diagram.addNode(newNode)  
    },
    getKnowledgeSpace(){
      console.log("usao")
      let config = { headers: comm.getHeader() }
      axios.get(comm.protocol +'://' + comm.server + '/knowledge-spaces/'+this.knowledgeSpaceId, config)
                .then(response => {
                if(response.status==200){
                  this.$refs.diagram.setModel(response.data)
                }
                }).catch(() => {
                alert("greska")
                })
    },
    submit(){
      if(this.knowledgeSpaceId)
        this.update();
      else
        this.create();
    },
    update(){
      let data = this.$refs.diagram.serialize()
      let config = {headers: comm.getHeader()}
      axios.put(comm.protocol +'://' + comm.server + '/knowledge-spaces/' + this.knowledgeSpaceId, data, config)
        .then(response => {
          console.log(response.data)
        }).catch(() => {
          alert("greska")
        })

    },
    create(){
      let data = this.$refs.diagram.serialize()
      let config = { headers: comm.getHeader() }
      axios.post(comm.protocol +'://' + comm.server + '/knowledge-spaces/course/'+this.courseId, data ,config)
                .then(response => {
                if(response.status==200){
                    alert("uspesno zavrsen prostor znanja")
                }
                }).catch(() => {
                alert("greska")
                })
    },
    getDomainProblems(){
      axios.get(comm.protocol +'://' + comm.server + '/courses/'+this.courseId+'/domain',{headers:comm.getHeader()})
        .then(response => {
          if(response.status==200){
              this.initDomainProblems(response.data)
          }
          }).catch(() => {
          alert("greska")
          })
    },
    initDomainProblems(domainProblems){
      let cord_x = 0 
      let cord_y = 0
      for(let dp of domainProblems){
        cord_y += 50.0
        cord_x += 15.0
        this.addNewNode(dp, {x:cord_x, y:cord_y})
      }
    }
  },
};
</script>

<style>

</style>