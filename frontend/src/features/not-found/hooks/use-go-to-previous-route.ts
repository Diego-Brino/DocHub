import {useNavigate} from "react-router-dom";

function useGoToPreviousRoute(){
  const navigate = useNavigate();

  return () => {
    navigate(-1);
  };
}

export {useGoToPreviousRoute}