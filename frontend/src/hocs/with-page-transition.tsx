import {ComponentType} from "react";
import {motion} from "framer-motion";
import {useLocation} from "react-router-dom";

const withPageTransition = <T extends object>(WrappedComponent: ComponentType<T>) => {
  return (props: T) => {
    const location = useLocation();

    const getFirstPath = () => {
      return location.pathname.split("/")[1];
    }

    return (
      <motion.div
        key={location.pathname}
        className='absolute h-screen w-screen'
        initial={{x: getFirstPath() === "login" ? "-100vw" : "100vw",}}
        animate={{x: "0",}}
        exit={{x: getFirstPath() === "login" ? "-100vw" : "100vw",}}
        transition={{duration: 1, ease: [0.22, 1, 0.36, 1]}}
      >
        <WrappedComponent {...props} />
      </motion.div>
    );
  };
};

export {withPageTransition};