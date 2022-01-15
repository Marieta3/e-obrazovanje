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
    <v-row class="d-flex justify-space-around">
        <v-col>
            <v-btn max-height="20" max-width="5"   :color="colors[0]"/>
            {{ks1Descript}}
        </v-col>
        <v-col>
            <v-btn max-height="20" max-width="5"  :color="colors[1]"/>
            {{ks2Descript}}
        </v-col>
        <v-col>
            <v-btn max-height="20" max-width="5"   :color="colors[2]"/>
            {{'same for both'}}
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
  props:['courseId'],
  components: {
    VueDiagramEditor,
  },
  data: () => ({
     nodes: {},
     common: [],
     ks1Links: [],
     ks2Links:[],
     sharedLinks: [],
     cubeSize: 10,
     colors: ['#457b9d',"#723d46","rgb(0,200,0)"],
     ks1Descript: "",
     ks2Descript: "",
     id: 1
  }),
  mounted() {
    this.init();
    this.getKnowledgeSpaces()
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

    nodePulsable() {
      return false
    },
    createLink(){
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
    async getKnowledgeSpaces(){
      console.log("usao")
      let config = { headers: comm.getHeader() }
      await axios.get(comm.protocol +'://' + comm.server + '/knowledge-spaces/compare-ks1/'+this.courseId, config)
                .then(response => {
                if(response.status==200){
                    console.log(response.data)
                    this.common = response.data.links 
                    let links = this.getLinks(response.data.ks1.links, response.data.ks2.links, response.data.links)
                    this.$refs.diagram.setModel({
                       nodes: response.data.ks2.nodes,
                       links: links
                    })
                    this.ks1Links = response.data.ks1.links
                    this.ks2Links = response.data.ks2.links
                    this.sharedLinks = response.data.links
                    this.ks1Descript = response.data.ks1.title
                    this.ks2Descript = response.data.ks2.title
                }
                }).catch(() => {
                alert("greska")
                })
                
       this.styleLinks(this.ks1Links, this.colors[0])
       this.styleLinks(this.ks2Links, this.colors[1])
       this.styleLinks(this.sharedLinks, this.colors[2])
        
    },
    styleLinks(links,style){ 
        for(let l of links){
            document.getElementById(l.id).children[1].style.stroke = style
        }
    },
    isNodeInCommonLinks(id){
        for(let l of this.common){
            if(l.start_id == id || l.end_id == id)
                return true
        }
        return false
    },
    getLinks(...links){
        let result = []
        for(let l of links){
            for(let con of l){
                con.id = "l"+con.id
                result.push(con)
            }
        }
        console.log("LINKOVI SU:")
        console.log(result)
        return result
    }
  },
};
</script>

<style>

</style>