import { parseResponse } from '../utils/utils';

const getHeaders = () => ({
  'Content-Type': 'application/json',
});

export const get = async(url) => {
  const options = {
    method: 'GET',
    headers: getHeaders()
  }
  const api = await fetch(url, options);
  const response = await parseResponse(api);
  return response
}
