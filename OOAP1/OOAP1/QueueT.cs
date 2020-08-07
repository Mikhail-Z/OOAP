using System;
using System.Collections.Generic;

namespace OOAP1_5
{
    public interface IQueue<T>
    {
        /// <summary>
        /// постусловие: в конец очереди добавлен новый элемент с указанным значением
        /// </summary>
        void Enqueue(T item);
        
        /// <summary>
        /// предусловие: очередь непустая
        /// постусловие: из начала очереди удален элемент
        /// </summary>
        T Dequeue();
        
        int Size();

        /// <summary>
        /// предусловие: список непустой
        /// </summary>
        T Head();
    }
    
    public enum OperationStatus
    {
        NIL,
        OK,
        ERR
    }
    
    public class Queue<T> : IQueue<T>
    {
        private Stack<T> stack1;
        private Stack<T> stack2;

        public Queue()
        {
            stack1 = new Stack<T>();
            stack2 = new Stack<T>();
        }
        
        public OperationStatus EnqueueStatus { get; private set; } = OperationStatus.NIL;
        public OperationStatus DequeueStatus { get; private set; } = OperationStatus.NIL;
        public OperationStatus HeadStatus { get; private set; } = OperationStatus.NIL;
        
        public void Enqueue(T item)
        {
            stack1.Push(item);
            EnqueueStatus = OperationStatus.OK;
        }

        public T Dequeue()
        {
            if (IsEmpty())
            {
                DequeueStatus = OperationStatus.ERR; 
                return default(T);
            }
            
            if (stack2.Count == 0)
            {
                MoveFirstStackToSecondStack();
            }

            DequeueStatus = OperationStatus.OK;
            return stack2.Pop();
        }

        public int Size()
        {
            return stack1.Count + stack2.Count;
        }

        public T Head()
        {
            if (IsEmpty())
            {
                HeadStatus = OperationStatus.ERR; 
                return default(T);
            }
            
            if (stack2.Count == 0)
            {
                MoveFirstStackToSecondStack();
            }

            HeadStatus = OperationStatus.OK;
            return stack2.Peek();
        }

        private bool IsEmpty()
        {
            return Size() == 0;
        }

        private void MoveFirstStackToSecondStack()
        {
            while (stack1.Count != 0)
            {
                stack2.Push(stack1.Pop());
            }
        }
    }
}