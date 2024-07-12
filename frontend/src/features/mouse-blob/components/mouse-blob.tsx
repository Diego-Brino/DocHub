import {useEffect, useRef} from "react";
import {lerp} from "../utils/utils.ts";

const LERP_AMOUNT = 0.03;

type MouseBlobProps = {
  offset: {
    x: number
    y: number
  }
}

function MouseBlob({offset}: MouseBlobProps) {
  const blobRef = useRef<HTMLDivElement>(null);
  const mousePositionRef = useRef({ x: 0, y: 0 });

  useEffect(() => {
    function moveBlob(){
      if(!blobRef.current){
        return;
      }

      const targetX = mousePositionRef.current.x + offset.x;
      const targetY = mousePositionRef.current.y + offset.y;

      const blobStyle = getComputedStyle(blobRef.current);
      const currentX = parseFloat(blobStyle.getPropertyValue('transform').split(',')[4]) || 0;
      const currentY = parseFloat(blobStyle.getPropertyValue('transform').split(',')[5]) || 0;

      const newX = lerp(currentX, targetX, LERP_AMOUNT);
      const newY = lerp(currentY, targetY, LERP_AMOUNT);

      blobRef.current.style.transform = `translate(${newX}px, ${newY}px)`;

      requestAnimationFrame(moveBlob);
    }

    function updateMousePosition(evt: MouseEvent) {
      mousePositionRef.current = {
        x: evt.clientX,
        y: evt.clientY
      };
    }

    document.addEventListener("mousemove", updateMousePosition);
    requestAnimationFrame(moveBlob);

    return () => document.removeEventListener("mousemove", updateMousePosition);
  }, []);

  return (
    <div ref={blobRef} className='absolute pointer-events-none'>
      <div
        className='relative h-44 w-44 rounded-full bg-black bg-gradient-to-bl from-[#e5d8fb] to-[#7c3aed] opacity-10 blur-3xl animate-blob'
      />
    </div>
  )
}

MouseBlob.displayName = "MouseBlob"

export {MouseBlob}
