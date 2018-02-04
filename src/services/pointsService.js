import { get } from './crudSvc';
import {logger} from '../utils/utils';

const PATH = 'https://devweek.pagekite.me/filter';
///

export const getPoint = async() => {
  // const url = `${PATH}/api/?${theParams(params)}`;
  const response = await get(PATH);
  logger('getPoint', response);
  return response
};
