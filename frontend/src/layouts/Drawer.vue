<template lang="">
  <div>
    <v-card>
      <v-navigation-drawer app permanent expand-on-hover>
        <v-list>
          <v-list-item class="px-2">
            <v-list-item-avatar>
              <v-img src="https://randomuser.me/api/portraits/women/85.jpg"></v-img>
            </v-list-item-avatar>
          </v-list-item>

          <v-list-item link>
            <v-list-item-content>
              <v-list-item-title> <h2>김민정</h2></v-list-item-title>
              <v-list-item-subtitle>
                <div style="font-size: 0.9rem">co323co@gmail.com</div>
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-list>

        <v-divider></v-divider>

        <v-list nav dense>
          <v-list-item link @click="move('main')">
            <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>
            <v-list-item-title>
              <div style="font-size: 1rem">홈</div>
            </v-list-item-title>
          </v-list-item>
          <v-list-item link @click="move('board')">
            <v-list-item-icon>
              <v-icon>mdi-book</v-icon>
            </v-list-item-icon>
            <v-list-item-title>
              <div style="font-size: 1rem">시리즈</div>
            </v-list-item-title>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>
    </v-card>
  </div>
</template>
<script>
export default {
  methods: {
    move(path) {
      //탭 메뉴들을 바꾸기 위해 알려줌
      if (path == 'main') this.$router.push({ path: `/main` }).catch(() => {});
      else if (path == 'board') this.$router.push({ path: `/board` }).catch(() => {});
    },
  },
};
</script>
<style lang=""></style>
