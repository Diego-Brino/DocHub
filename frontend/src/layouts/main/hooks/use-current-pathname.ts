import {useLocation} from "react-router-dom";


function useCurrentPathname(){
  const location = useLocation();

  return location.pathname;
}

export {useCurrentPathname}