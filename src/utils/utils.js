export const parseResponse = async (response) => {
  let newResponse = {};
  if (response.ok) {
    newResponse = {
      ok: true,
      data: await response.json()
    };
  } else {
    newResponse ={
      ok: false,
      data: {}
    };
  }
  return newResponse
}

export const logger = (text,log) => console.log(`%c log ${text}`, 'color: #f7f7f7; background: #006633', log)

export const theParams = (params) => {
  let esc = encodeURIComponent;
  return Object.keys(params)
    .map(k => `${esc(k)}=${esc(params[k])}`)
    .join('&');
}
