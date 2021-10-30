import axios from '@/utils/axios';

// 모든 시리즈 목록 조회 API
function getSeriesList() {
  return axios.get('/series');
}

export { getSeriesList };
