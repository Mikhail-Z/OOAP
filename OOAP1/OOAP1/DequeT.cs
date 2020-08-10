using System.Collections.Generic;
using System.Linq;

namespace OOAP1_6
{
    public enum OperationStatus
    {
        NIL,
        OK,
        ERR
    }
    
    public interface IQueue<T>
    {
        /// <summary>
        /// предусловие: очередь непустая
        /// постусловие: из начала очереди удален элемент
        /// </summary>
        T RemoveFront();
        
        /// <summary>
        /// постусловие: в конец очереди добавлен элемент
        /// </summary>
        void AddTail(T item);
        
        int Size();

        /// <summary>
        /// предусловие: очередь непустая
        /// </summary>
        T Head();
    }

    public interface IDeque<T> : IQueue<T>
    {
        /// <summary>
        /// постусловие: в начало дека добавлен элемент
        /// </summary>
        void AddHead(T value);
        
        /// <summary>
        /// предусловие: дек непустой
        /// </summary>
        T RemoveTail();
        
        /// <summary>
        /// предусловие: дек непустой
        /// </summary>
        T Tail();
    }
    
    public class ParentQueue<T> : IQueue<T>
    {
        protected List<T> list;

        public OperationStatus RemoveFrontStatus { get; protected set; } = OperationStatus.NIL;
        public OperationStatus AddTailStatus { get; private set; } = OperationStatus.NIL;
        public OperationStatus HeadStatus { get; private set; } = OperationStatus.NIL;
        
        public ParentQueue()
        {
            list = new List<T>();
        }
        
        public T RemoveFront()
        {
            if (list.Count == 0)
            {
                RemoveFrontStatus = OperationStatus.ERR;
                return default;
            }

            var head = list.First();
            list.RemoveAt(0);
            RemoveFrontStatus = OperationStatus.OK;
            return head;
        }

        public void AddTail(T item)
        {
            list.Add(item);
            AddTailStatus = OperationStatus.OK;
        }

        public int Size()
        {
            return list.Count;
        }

        public T Head()
        {
            if (list.Count == 0)
            {
                HeadStatus = OperationStatus.ERR;
            }

            HeadStatus = OperationStatus.OK;
            return list.First();
        }
    }

    public class Queue<T> : ParentQueue<T>
    {
        
    }
    
    public class Deque<T> : ParentQueue<T>, IDeque<T>
    {
        public OperationStatus AddHeadStatus { get; private set; }
        public OperationStatus RemoveTailStatus { get; private set; }
        public OperationStatus TailStatus { get; private set; }
        
        public void AddHead(T value)
        {
            list.Insert(0, value);
        }

        public T RemoveTail()
        {
            if (list.Count == 0)
            {
                RemoveTailStatus = OperationStatus.ERR;
                return default;
            }

            var tail = list.Last();
            list.RemoveAt(list.Count - 1);
            RemoveTailStatus = OperationStatus.OK;
            return tail;
        }

        public T Tail()
        {
            if (list.Count == 0)
            {
                TailStatus = OperationStatus.ERR;
                return default;
            }

            TailStatus = OperationStatus.OK;
            return list.Last();
        }
    }
}