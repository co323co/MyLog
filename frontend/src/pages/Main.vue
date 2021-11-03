<template lang="">
  <div class="pa-2 my-5">
    <v-row>
      <v-col cols="1">
        <CatAni />
      </v-col>
    </v-row>
    <div class="text-center" style="margin-top: 60px">
      <h2 class="mt-5">만나서 반가워요!</h2>
      <h2 style="display: inline">성장하는 개발자</h2>
      <h2 class="ml-1" style="color: orange; display: inline">김민정의 블로그</h2>
      <h2 class="ml-1" style="display: inline">입니다</h2>
      <h2 class="pt-3" style="margin: 60px 0px">저는 이런사람 이에요!</h2>
    </div>

    <div class="text-center pa-5 mt-5">
      <!-- <h1>기술스택</h1> -->
      <v-row>
        <v-col>
          <HoverCard :content="card1" />
        </v-col>
        <v-col>
          <HoverCard :content="card2" />
        </v-col>
        <v-col>
          <HoverCard :content="card3" />
        </v-col>
      </v-row>
      <div style="margin-top: 100px">
        <h3>백엔드 개발자로서 더 성장하기 위해 항상 고민하고 있어요</h3>
        <h3 class="my-3">
          <a @click="moveToGithub"> 깃허브 </a>
          에서 제가 했던 프로젝트를 구경할 수 있어요!
        </h3>
      </div>
      <div class="text-center pa-5" style="margin: 60px 10%">
        <v-carousel :cycle="true" :hide-delimiter-background="true">
          <v-carousel-item
            v-for="(item, i) in items"
            :key="i"
            :src="item.src"
            reverse-transition="fade-transition"
            transition="fade-transition"
          ></v-carousel-item>
        </v-carousel>
      </div>
    </div>
  </div>
</template>
<script>
import CatAni from '@/components/main/CatAni';
import HoverCard from '@/components/main/HoverCard';
import pic1 from '@/assets/img/GAON.png';
import pic2 from '@/assets/img/GAON2.png';
import pic3 from '@/assets/img/CafeIn.png';

export default {
  components: {
    CatAni,
    HoverCard,
  },
  data() {
    return {
      reveal: false,
      items: [
        {
          src: pic1,
        },
        {
          src: 'https://user-images.githubusercontent.com/86418158/125574224-2047eae4-460e-418f-8be5-b239e5ecba9a.png',
        },
        {
          src: pic2,
        },
        {
          src: pic3,
        },
      ],
      card1: {
        title: `🧹클린코드`,
        content: `더 간결하고 이해하기 쉬운 코드를 원해요<br />
          지금의 방법보다 더 좋은 방법을 찾기 위해 노력해요<br />
          목표는 미래의 내가 봐도<br />
          한번에 이해할 수 있는 코드에요!<br/><br/>`,
      },
      card2: {
        title: `✨새로운 기술`,
        content: `다양한 기술을 접하고 시도해보는 걸 좋아해요 <br/>
          불편함을 해소하기 위한 기술을 찾아보고<br/>적용해보는 걸 즐겨요<br/>
          예를 들면 mybatis로 작성했던 프로젝트를<br/> jpa/querydsl로 리팩토링 해본 적이 있어요!`,
      },
      card3: {
        title: `👩‍👧‍👦스터디 러브`,
        content: `혼자하는 공부도 좋지만 여럿이 함께하는걸 더 좋아해요<br />
          코드리뷰를 통해 새로운 멋진 방식을 배우기도 하고</br>
          혹은 내 코드를 설명하면서 복습할 수 있어요</br>
          지식 교류를 통해 볼 수 있는 세계가 더 넓어져요!<br/><br/>`,
      },
    };
  },
  methods: {
    moveToGithub() {
      window.open('https://github.com/co323co');
    },
  },
};
</script>
<style scoped>
.font {
  font-family: IBMPlexSansKR !important;
}
.v-card--reveal {
  bottom: 0;
  opacity: 1 !important;
  position: absolute;
  width: 100%;
}
</style>
