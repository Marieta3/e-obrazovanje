<template>
  <v-form>
      <v-text-field
            v-model="node.title"
            label="Title"
            @change="emitChange()"
        ></v-text-field>
    <v-textarea
            v-model="node.description"
            label="Description"
            @change="emitChange()"
        ></v-textarea>
  </v-form>
</template>

<script>
export default {
    data(){
        return{
            node: {
                title: "",
                description: ""
            }
        }
    },
    methods:{
        emitChange(){         
            this.$emit('dataChanged',this.node)
        }
    }



}
</script>

<style>

</style>