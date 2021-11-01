import axios from '@/utils/axios';

// 모든 시리즈 목록 조회 API
async function getSeriesList() {
  try {
    const { data } = await axios.get('/series');
    return data.result;
  } catch (error) {
    console.error(error);
  }
}

// 시리즈 이름 조회
async function getSeriesName(seriesId) {
  try {
    const { data } = await axios.get(`/series/${seriesId}`);
    return data.result;
  } catch (error) {
    console.error(error);
  }
}

export { getSeriesList, getSeriesName };
