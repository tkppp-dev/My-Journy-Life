<template>
  <base-modal :show="show">
    <template v-slot:modal-content>
      <div class="content-container">
        <div class="content" @click.stop>
          <input
            class="search-bar"
            type="text"
            placeholder="리뷰를 검색해보세요!"
            v-model="searchVal"
            @keyup.enter="pressEnter"
          />
        </div>
      </div>
    </template>
  </base-modal>
</template>

<script>
import BaseModal from './BaseModal.vue';
export default {
  name: 'SearchModal',
  components: {
    BaseModal,
  },
  props: {
    show: Boolean,
  },
  data() {
    return {
      searchVal: ''
    }
  },
  methods: {
    pressEnter(){
      console.log(this.searchVal)
    }
  }
};
</script>

<style>
.content-container {
  display: flex;
  margin-top: 112px;
  justify-content: center;
}

.content {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 800px;
  height: 150px;
  border-radius: 12px;
  background-color: rgba(226, 216,190,0.6);

  box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.3);
}

.search-bar-icon {
  position: absolute;
}

.search-bar {
  width: 640px;
  height: 30px;
  padding: 12px 12px 12px 56px;
  border: 0px;
  border-radius: 6px;
  font-size: 18px;
  box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.3);

  /* search icon */
  background-image: url('../../assets/icons/search_icon.png');
  background-repeat: no-repeat;
  background-position: 16px center;
  background-size: 27px;
}

.search-bar:focus {
  outline: none;
}

.search-bar::placeholder {
  font-size: 20px;
}
</style>
