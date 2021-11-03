<template>
  <v-form>
    <v-container>
      <v-row justify="center">
        <v-col cols="12" md="8" >
          <v-text-field
            v-model="name"
            label="Name"
            required
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="12" md="8" >
         <v-combobox v-model="select" :items="allTeachers" label="Teacher"></v-combobox>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="12" md="8" >
          <v-textarea
            filled
            v-model="description"
            label="Description"
            ></v-textarea>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="12" sm="6" >
            <v-btn color="primary" @click="create()">Create</v-btn>
        </v-col>
       </v-row>
    </v-container>
  </v-form>
</template>

<script>
export default {
    name:"CreateCourse",
    data () {
      return {
        name : "",
        description: "",
        select: "",
        allTeachers: [
          'Programming',
          'Design',
          'Vue',
          'Vuetify',
        ],
      }
    }
}
</script>

<style>

</style>