import axios from '@/utils/axios';

// 모든 시리즈 목록 조회 API
// function getSeriesList() {
//   console.log(process.env.VUE_APP_API_URL + '/api/series');
//   return axios.get('/series');
// }

// 모든 시리즈 목록 조회 API
async function getSeriesList() {
  try {
    const { data } = await axios.get('/series');
    return data.result;
  } catch (error) {
    console.error(error);
  }
}
export { getSeriesList };
