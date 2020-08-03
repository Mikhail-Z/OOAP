using System;
using System.Collections.Generic;
using System.Linq;

namespace OOAP1
{
    /// <summary>
    /// Реализация абстрактного типа данных - Bounded Stack (стек с заданной максимальной глубиной)
    /// </summary>
    public class BoundedStack<T>
    {
        private List<T> stack;
        private int maxDepth;
        
        public const int POP_NIL = 0;
        public const int POP_OK = 1;
        public const int POP_ERR = 2;
        public const int PEEK_NIL = 0;
        public const int PEEK_OK = 1;
        public const int PEEK_ERR = 2;
        public const int PUSH_NIL = 0;
        public const int PUSH_OK = 1;
        public const int PUSH_ERR = 2;


        public const int DEFAULT_MAX_DEPTH = 32;

        /// <summary>
        /// постусловие: создан стек с ограничением по глубине
        /// </summary>
        private BoundedStack() : this(DEFAULT_MAX_DEPTH) {}

        /// <summary>
        /// постусловие: создан стек с ограничением по глубине
        /// </summary>
        private BoundedStack(int maxDepth)
        {
            this.maxDepth = maxDepth;
            Clear();
        }
        
        /// <summary>
        /// постусловие: из стека удалятся все значения
        /// </summary>
        public void Clear()
        {
            this.stack = new List<T>();
            PeekStatus = PEEK_NIL;
            PopStatus = POP_NIL;
            PushStatus = PUSH_NIL;
        }

        /// <summary>
        /// предусловие: глубина стека меньше максимальной
        /// постусловие: в стек добавлено новое значение
        /// </summary>
        public void Push(T value)
        {
            if (stack.Count == maxDepth)
            {
                PushStatus = PUSH_ERR;
                return;
            }

            PushStatus = PUSH_OK;
            this.stack.Append(value);
        }

        /// <summary>
        /// предусловие: стек непустой
        /// постусловие: из стека удалено значение, которое было последним добавлено
        /// </summary>
        public void Pop()
        {
            if (Size() > 0)
            {
                stack.RemoveAt(-1);
                this.PopStatus = POP_OK;
            }
            else
            {
                this.PopStatus = POP_ERR;
            }
        }

        /// <summary>
        /// предусловие: стек непустой
        /// </summary>
        public T Peek()
        {
            if (Size() > 0)
            {
                var value = stack[0];
                this.PopStatus = PEEK_OK;
                return value;
            }
            else
            {
                this.PopStatus = PEEK_ERR;
                return default(T);
            }
        }

        public int Size()
        {
            return this.stack.Count;
        }
        
        public int PeekStatus { get; private set; }
        
        public int PopStatus { get; private set; }
        
        public int  PushStatus { get; private set; }

        public class BoundedStackFactory
        {
            public const int CREATE_OK = 0;
            public const int CREATE_NIL = 1;
            public const int CREATE_ERR = 2;
        
            /// <summary>
            /// постусловие: создана фабрика по созданию ограниченных стеков
            /// </summary>
            public BoundedStackFactory()
            {
                CreateStatus = CREATE_NIL;
            }

            /// <summary>
            /// постусловие: создан ограниченный стек
            /// </summary>
            public BoundedStack<T> Create(int maxDepth)
            {
                if (maxDepth < 1)
                {
                    CreateStatus = CREATE_ERR;
                    return null;
                }

                CreateStatus = CREATE_OK;
                return new BoundedStack<T>(maxDepth);
            }
        
            public int CreateStatus { get; private set; }
        }
    }
}