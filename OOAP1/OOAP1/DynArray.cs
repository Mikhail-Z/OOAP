using System;

namespace OOAP1_4
{
    public interface IDynArray<T>
    {
        /// <summary>
        /// предусловие: текущее кол-во элементов в массиве больше или равно указанноного индекса
        /// </summary>
        T GetItem(int index);
        
        /// <summary>
        /// постусловие: в конец массива добавлен новый элемент  
        /// </summary>
        void Append(T value);
        
        /// <summary>
        /// предусловие: текущее кол-во элементов в массиве больше, равно или меньше на 1 указанноного индекса
        /// постусловие: по указанному индексу добавлен новый элемент, при этом элементы правее сдвигаются
        /// </summary>
        void Insert(T value, int index);
        
        /// <summary>
        /// предусловие: текущее кол-во элементов в массиве больше или равно указанному индекса
        /// постусловие: удален элемент по указанному индексу, элементы правее сдвинулись левее
        /// </summary>
        void Remove(int index);
    }
    
    public enum OperationStatus
    {
        NIL,
        OK,
        ERR
    }
    
    public sealed class DynArray<T> : IDynArray<T>
    {
        public T[] array;
        public int count;
        public int capacity;

        private const int MIN_CAPACITY = 16;
        private const int MAGNIFICATION_FACTOR = 2;
        private const double REDUCTION_FACTOR = 1.5;
        
        public OperationStatus GetItemStatus { get; private set; }
        public OperationStatus AppendStatus { get; private set; }
        public OperationStatus InsertStatus { get; private set; }
        public OperationStatus RemoveStatus { get; private set; }
        
        private DynArray() : this(MIN_CAPACITY)
        {
        }

        private DynArray(int capacity)
        {
            count = 0;
            MakeArray(capacity);
        }

        public T GetItem(int index)
        {
            if (index < 0 || index >= count)
            {
                GetItemStatus = OperationStatus.ERR;
                return default;
            }

            GetItemStatus = OperationStatus.OK;
            return array[index];
        }

        public void Append(T itm)
        {
            Insert(itm, count);
            AppendStatus = InsertStatus;
        }

        public void Insert(T itm, int index)
        {
            if (index < 0 || index > count)
            {
                InsertStatus = OperationStatus.ERR;
                return;
            }

            if (count == capacity)
            {
                var newCapacity = capacity * MAGNIFICATION_FACTOR;
                try
                {
                    MakeArray(newCapacity);
                }
                catch (Exception ex)
                {
                    InsertStatus = OperationStatus.ERR;
                    return;
                }
            }

            MoveItemsRight(index);
            array[index] = itm;
            count++;
            InsertStatus = OperationStatus.OK;
        }

        public void Remove(int index)
        {
            if (index < 0 || index >= count)
            {
                RemoveStatus = OperationStatus.ERR;
                return;
            }

            MoveItemsLeft(index);
            count--;

            if (count * 2 < capacity)
            {
                int newCapacity = (int)(capacity / REDUCTION_FACTOR);
                try
                {
                    MakeArray(newCapacity); 
                }
                catch (ArgumentException ex)
                {
                    RemoveStatus = OperationStatus.ERR;
                    return;
                }
            }

            RemoveStatus = OperationStatus.OK;
        }

        private void MakeArray(int newCapacity)
        {
            if (newCapacity < count || newCapacity <= 0)
            {
                throw new ArgumentException();
            }

            var actualNewCapaciity = newCapacity > MIN_CAPACITY ? newCapacity : MIN_CAPACITY;

            if (array != null)
            {
                var newArray = new T[actualNewCapaciity];
                Array.Copy(array, newArray, count);
                array = newArray;
            }
            else
            {
                array = new T[actualNewCapaciity];
            }
            
            capacity = actualNewCapaciity;
        }
        
        private void MoveItemsRight(int index)
        {
            for (int i = count; i > index; i--)
            {
                array[i] = array[i-1];
            }
            array[index] = default(T);
        }

        private void MoveItemsLeft(int index)
        {
            for (int i = index; i < count-1; i++)
            {
                array[i] = array[i+1];
            }

            array[count - 1] = default(T);
        }
        
        public class DynArrayFactory
        {
            public OperationStatus CreateStatus { get; private set; } = OperationStatus.NIL;
        
            public DynArray<T> Create<T>()
            {
                CreateStatus = OperationStatus.OK;
                return new DynArray<T>();
            }

            public DynArray<T> Create<T>(int capacity)
            {
                if (capacity <= 0)
                {
                    CreateStatus = OperationStatus.ERR;
                    return null;
                }

                CreateStatus = OperationStatus.OK;
                return new DynArray<T>(capacity);
            }
        }
    }
}