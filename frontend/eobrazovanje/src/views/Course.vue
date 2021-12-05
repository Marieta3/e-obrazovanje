<template>
  <v-container>
      <v-row>
          <v-col cols="12" sm="3" md="3">
              <side-btn-menu :courseId="courseId"/>
          </v-col>
          <v-col cols="12" sm="9" md="9">
              <router-view :courseId="courseId"/>
          </v-col>
      </v-row>
  </v-container>
</template>

<script>
import SideBtnMenu from '../components/course/SideBtnMenu.vue'
export default {
  components: { SideBtnMenu },
  props: ['courseId']

}
</script>

<style>

</style>