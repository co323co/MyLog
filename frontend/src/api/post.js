import axios from '@/utils/axios';

// 게시글 리스트 조회 API
function getPostList(seriesId, hasSeries, search, page, size) {
  var url = `/posts?page=${page}&size=${size}`;
  if (seriesId) url += `&seriesId=${seriesId}`;
  if (hasSeries) url += `&hasSeries=${hasSeries}`;
  if (search) url += `&search=${search}`;
  console.log(url);
  return axios.get(url);
}

export { getPostList };
